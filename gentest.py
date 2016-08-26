#!usr/bin/env python
import sys
import random

# Usage:
# python gentest.py <op1,op2,op3> <num> <output file>
# i.e. python gentest.py av,ae 10000 tests/customtest.in
# output file should end with .in

ops = ["av", "ae"]
num = 100000
outfile = "tests/customtest.in"

if len(sys.argv) > 3:
    outfile = sys.argv[3]
if len(sys.argv) > 2:
    num = int(sys.argv[2])
if len(sys.argv) > 1:
    ops = sys.argv[1].split(",")

output = ""

for op in ops:
    for i in range(num):
        if op == "ae":
            output += "ae %d %d\n" % (random.randint(0, i), random.randint(0, i))
        else:
            output += "%s %d\n" % (op, i)

with open(outfile, "w") as f:
    f.write(output)

with open(outfile[:-3] + ".neigh.exp", "w") as f:
    f.write("expected neighbors not implemented")

with open(outfile[:-3] + ".vert.exp", "w") as f:
    f.write("expected verts not implemented")

with open(outfile[:-3] + ".dist.exp", "w") as f:
    f.write("expected dist not implemented")

with open(outfile[:-3] + ".edge.exp", "w") as f:
    f.write("expected edges not implemented")

print "Wrote %d %s operations to %s" % (num, ops, outfile)
