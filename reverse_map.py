import os
import xml.etree.ElementTree as ET
import json
from collections import defaultdict

# Directory containing JaCoCo XMLs
XML_DIR = "reverse-mapping"
OUTPUT_FILE = os.path.join(XML_DIR, "method_test_mapping.json")

mapping = defaultdict(set)

for file in os.listdir(XML_DIR):
    if not file.endswith(".xml"):
        continue

    test_name = file[:-4]  # Remove ".xml"
    tree = ET.parse(os.path.join(XML_DIR, file))
    root = tree.getroot()

    for pkg in root.findall(".//package"):
        pkg_name = pkg.get('name').replace('/', '.')
        for cls in pkg.findall("class"):
            class_name = cls.get('name').replace('/', '.')
            if "MethodCallTracker" in class_name:
                continue

            # Prevent double package prefix
            if class_name.startswith(pkg_name):
                full_class = class_name
            else:
                full_class = f"{pkg_name}.{class_name}"

            for method in cls.findall("method"):
                method_name = method.get('name')
                if method_name == "<init>":
                    continue

                # Check coverage only if covered instructions > 0
                instr = method.find("counter[@type='INSTRUCTION']")
                if instr is None or int(instr.get('covered')) == 0:
                    continue

                fqmn = f"{full_class}.{method_name}"
                mapping[fqmn].add(test_name)

# Convert sets to sorted lists
final_mapping = {k: sorted(list(v)) for k, v in mapping.items()}

# Write output
os.makedirs(XML_DIR, exist_ok=True)
with open(OUTPUT_FILE, "w") as f:
    json.dump(final_mapping, f, indent=2)

print("Generated mapping written to", OUTPUT_FILE)
