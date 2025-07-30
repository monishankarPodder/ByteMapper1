import os
import csv
import json
from collections import defaultdict

mapping = defaultdict(set)

for file in os.listdir('method-mapping'):
    if file.endswith(".csv"):
        test = file.replace('.csv', '')
        with open(f'method-mapping/{file}', newline='') as csvfile:
            reader = csv.DictReader(csvfile)
            for row in reader:
                if row['METHOD'] != '<init>':  # Skip constructors
                    fq_method = row['CLASS'] + "#" + row['METHOD']
                    mapping[fq_method].add(test)

# Convert sets to lists
final_mapping = {k: list(v) for k, v in mapping.items()}

os.makedirs("app/target", exist_ok=True)
with open('app/target/method_test_mapping.json', 'w') as f:
    json.dump(final_mapping, f, indent=2)

print("✅ method_test_mapping.json generated")
