import sys

from main import basic_listen_all

sys.path.append("/home/pi/mqtt_env")

if __name__ == "__main__":
    basic_listen_all.start()
