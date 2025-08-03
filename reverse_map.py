import os
import xml.etree.ElementTree as ET
import json

mapping = {}

for file in os.listdir("reverse-mapping"):
    if not file.endswith(".xml"):
        continue
    test_name = file[:-4]  # Remove .xml
    tree = ET.parse(os.path.join("reverse-mapping", file))
    root = tree.getroot()
    for pkg in root.findall(".//package"):
        pkg_name = pkg.get("name").replace("/", ".")
        for cls in pkg.findall("class"):
            cls_name = cls.get("name")
            for method in cls.findall("method"):
                method_name = method.get("name")
                fqmn = f"{pkg_name}.{cls_name}.{method_name}"
                mapping.setdefault(fqmn, []).append(test_name)

# Save output
os.makedirs("reverse-mapping", exist_ok=True)
with open("reverse-mapping/method_test_mapping.json", "w") as f:
    json.dump(mapping, f, indent=2)

print("✅ method_test_mapping.json generated successfully")
