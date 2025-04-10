from enum import Enum


class ActivityState(Enum):
    ACTIVE: str = "Connected"
    INACTIVE: str = "Disconnected"
