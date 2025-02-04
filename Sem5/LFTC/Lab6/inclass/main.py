import re
from collections import deque

def parse_cfg(file_path: str):
    non_terminals = set()
    terminals = set()
    production_rules = {}
    start_symbol = None

    production_pattern = re.compile(r"(\S+)\s*->\s*(.+)")

    with open(file_path, "r", encoding="utf-8") as file:
        for line_num, line in enumerate(file):
            line = line.strip()
            if not line:
                continue

            match = production_pattern.match(line)
            if not match:
                print(f"Invalid line skipped: {line}")
                continue

            lhs, rhs = match.groups()

            if line_num == 0:
                start_symbol = lhs

            non_terminals.add(lhs)
            production_rules.setdefault(lhs, []).extend([rhs])

            symbols = rhs.split(" ")
            for symbol in symbols:
                if symbol.isupper():
                    non_terminals.add(symbol)
                else:
                    terminals.add(symbol.strip("'"))

    # VERIFICARE ACCESIBILITATE
    accessible_non_terminals = set()

    if start_symbol:
        queue = deque([start_symbol])
        while queue:
            current = queue.popleft()
            if current in accessible_non_terminals:
                continue

            accessible_non_terminals.add(current)

            for production in production_rules.get(current, []):
                symbols = production.split(" ")
                for symbol in symbols:
                    if symbol in non_terminals and symbol not in accessible_non_terminals:
                        queue.append(symbol)

    return {
        "non_terminals": non_terminals,
        "terminals": terminals - non_terminals,
        "production_rules": production_rules,
        "start_symbol": start_symbol,
        "inaccessible_non_terminals":  non_terminals - accessible_non_terminals,
    }

# Example usage
grammar = parse_cfg("grammar.txt")
print("=" * 20, "\nNon-Terminals:")
print(grammar["non_terminals"])
print("\nTerminals:")
print(grammar["terminals"])
print("\nProduction Rules:")
for non_terminal, rules in grammar["production_rules"].items():
    print(f"{non_terminal} -> {' | '.join(rules)}")
print("\nStart Symbol:")
print(grammar["start_symbol"])
print("\nInaccessible Symbols:")
print("Non-Terminals:", grammar["inaccessible_non_terminals"] if grammar["inaccessible_non_terminals"] else "None")
print("=" * 20, end="")

# LR(1)