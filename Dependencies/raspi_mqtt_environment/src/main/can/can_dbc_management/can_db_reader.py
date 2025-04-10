import can
import cantools

from ...utils.helper_functions import get_project_path_for

CAN_DBC_FILE_PATH = 'can_dbc/EApeCanDBC.dbc'


class CANDbReader:
    db_path: str
    db: cantools.db.can.database.Database = None

    def __init__(self, dbc_file_path: str = CAN_DBC_FILE_PATH):
        can_dbc = get_project_path_for(__file__, dbc_file_path)
        self.db_path = str(can_dbc)
        self.db = cantools.db.load_file(can_dbc)

    def interpret_can_message(self, can_message: can.Message):
        if not self.db:
            return {"error": f"CAN-DBC (path: '{self.db_path}') not initialized."}
        try:
            decoded_can_message = self.db.decode_message(can_message.arbitration_id, can_message.data)
        except KeyError:
            return {"error": "KeyError - CAN-ID not defined in DBC."}
        return decoded_can_message
