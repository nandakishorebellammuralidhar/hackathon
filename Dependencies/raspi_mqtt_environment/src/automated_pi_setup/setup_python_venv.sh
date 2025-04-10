#!/bin/bash
echo "################ Python Venv Setup #############"
(
python3 -m venv ".venv"
source ./.venv/bin/activate
.venv/bin/pip install -r ./requirements.txt
)

echo "##################################################"