import os
import xml.etree.ElementTree as ET
import json
from collections import defaultdict

mapping = defaultdict(set)

for file in os.listdir("reverse-mapping"):
    if not file.endswith(".xml"):
        continue

    test_name = file[:-4]
    tree = ET.parse(os.path.join("reverse-mapping", file))
    root = tree.getroot()

    for pkg in root.findall(".//package"):
        pkg_name = pkg.get('name').replace('/', '.')
        for cls in pkg.findall("class"):
            class_name = cls.get('name').replace('/', '.')
            if "MethodCallTracker" in class_name:
                continue  # Skip helper/logging classes

            for method in cls.findall("method"):
                method_name = method.get('name')
                if method_name == "<init>":
                    continue  # Skip constructors

                # ✅ Check coverage: only map the method if it was actually hit
                instr_counter = method.find("counter[@type='INSTRUCTION']")
                if instr_counter is None:
                    continue
                covered = int(instr_counter.get('covered'))
                if covered == 0:
                    continue

                fqmn = f"{pkg_name}.{class_name}.{method_name}"
                mapping[fqmn].add(test_name)

# Convert to normal dict
final_mapping = {k: sorted(list(v)) for k, v in mapping.items()}

with open("reverse-mapping/method_test_mapping.json", "w") as f:
    json.dump(final_mapping, f, indent=2)
