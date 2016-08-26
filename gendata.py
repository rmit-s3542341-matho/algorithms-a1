#!usr/bin/env python

import sys
import random

from time import time

# Generates a set of vertices and edges that will
# have a density of that specified.
#
# Usage:
#      python gendata.py <num vertices> <density %> <output file>
# i.e. python gendata.py 4038 0.5411 facebook_combinerino.txt

n_vertices  = 4038
density     = 0.5411 / 100 # divide by 100 to return to decimal
output_file = "facebook_combinerino.txt"

# get arguments
try:
    n_vertices  = int(sys.argv[1])
    density     = float(sys.argv[2]) / 100
    output_file = sys.argv[3]

except IndexError, e:
    pass

# calculate expected number of edges
n_edges = int(density * n_vertices * n_vertices)
print "Creating dataset with %d vertices and %d edges (%.4f%% density)..." % \
    (n_vertices, n_edges, density * 100)

# list of current edges (as tuples), to avoid duplicates
edges = []

# determine position to keep cursor (bash ONLY)
print "\033[s",

s_time = time() * 1000

# build the list of unique tuples
for i in range(n_edges):
    s_v = random.randint(0, n_vertices) # start vertex (first of pair)
    e_v = random.randint(0, n_vertices) # end vertex (last of pair)
    # continue guessing for e_v until a unique edge appears
    while e_v == s_v or (s_v, e_v) in edges:
        e_v = random.randint(0, n_edges)
    # add the edge
    edges.append((s_v, e_v))
    print "\033[u%d of %d (%0.4f%%) %dms" % (i + 1, n_edges,
        float(i + 1) / n_edges * 100, time() * 1000 - s_time),

print "\nSorting list... %dms" % (time() * 1000 - s_time)
# sort list of tuples (for shits and giggles)
edges.sort(key=lambda x: x[0] * n_vertices + x[1])

print "Writing... %dms" % (time() * 1000 - s_time)
# write to output file
with open(output_file, "w") as out:
    for edge in edges:
        out.write("%d %d\n" % edge)

print "Done. %dms" % (time() * 1000 - s_time)
