# shell script to test all data + tests generated by gendatasets.sh,
# using the implementation given as the second parameter.
# assumes this is executed in the _same directory_ as assign1TestScript.py
# e.g. ./test-all.sh adjlist

# check argument length
[ $# -eq 0 ] && echo "Usage: ./test-all.sh [adjlist|adjmat]" && exit

echo "Testing all data and outputting to test_output_$1.txt"
echo "May take a minute."

# clear the output file
echo "" > "test_output_$1.txt"

for density in $(seq 0.5 0.5 2.5)
do
	for n_vertices in {1000..3000..500}
	do
		echo "Data for $n_vertices vertices with $density% density\n=============================================" >> "test_output_$1.txt"
		python assign1TestScript.py Assign1-s3542341-s3542871/ $1 -f "../data-$n_vertices-$density.txt" "tests/test_data-$n_vertices-$density.in" >> "test_output_$1.txt"
	done
done