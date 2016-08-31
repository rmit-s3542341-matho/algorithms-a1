# shell script to generate data sets for the vertex 
# counts and densities specified.
# places all the data sets "data-####-##.txt" in the current directory
# and all the tests "test_data-####-##.in / exp" in the tests/ folder
# best if executed in the same directory as assign1TestScript.py

echo "Placing data sets in the current directory"
echo "And test .in files in tests/\n"
echo "Generating all data sets..."

for density in $(seq 0.5 0.5 2.5)
do
	for n_vertices in {1000..3000..500}
	do
		python gendata.py $n_vertices $density "data-$n_vertices-$density.txt"
	done
done