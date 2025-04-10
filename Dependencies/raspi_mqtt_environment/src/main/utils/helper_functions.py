import json
import logging
from pathlib import Path

LOGGER = logging.getLogger(__name__)
LOGGER.setLevel(logging.INFO)
HANDLER = logging.StreamHandler()
FORMATTER = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
HANDLER.setFormatter(FORMATTER)
LOGGER.addHandler(HANDLER)


def read_json_from_file(filename):
    try:
        with open(filename, "r", encoding="utf-8") as f:
            data = json.load(f)
            return data

    except FileNotFoundError:
        LOGGER.error(f"Error: File '{filename}' not found.")
        return None

    except json.JSONDecodeError as e:
        LOGGER.error(f"Error: Invalid JSON format in '{filename}': {e}")
        return None

    except KeyError as e:
        LOGGER.error(f"Error: Key '{e}' not found in JSON data.")
        return None

    except Exception as e:
        LOGGER.error(f"An unexpected error occurred: {e}")
        return None


def get_all_can_frame_ids(frame_ids_and_topics_json_path: Path) -> list[str]:
    frame_id_dict = read_json_from_file(frame_ids_and_topics_json_path)
    return [str(correct_identifier(hex(int(key)))) for key in
            frame_id_dict.keys()] if frame_id_dict is not None else []


def correct_identifier(frame_id: str) -> hex:
    can_id_29_bit = (int(frame_id, 16) & 0x1FFFFFFF)
    separate_identifier = 0x18
    combined_value = (separate_identifier << 24) | can_id_29_bit
    return hex(combined_value)


def int_to_hex_string(can_id_int_string: str) -> str:
    return str(hex(int(can_id_int_string)))


def get_project_root(file: str, parent_level: int = 0) -> Path:
    current_file = Path(file).resolve()
    project_root = current_file.parents[parent_level]
    return project_root


def get_project_path_for(current_file: str, relative_path: str, parent_level: int = 0) -> Path:
    project_root = get_project_root(current_file, parent_level=parent_level)
    return project_root / relative_path
