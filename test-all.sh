echo "Testing all data and outputting to test_outputs.txt"
echo "May take a minute."

# clear file
echo "" > test_outputs.txt

for density in $(seq 0.5 0.5 2.5)
do
	for n_vertices in {1000..3000..500}
	do
		echo "Data for $n_vertices vertices with $density% density" >> test_outputs.txt
		echo "=============================================" >> test_outputs.txt
		python27 assign1TestScript.py Assign1-s3542341-s3542871/ $1 -f "../data-$n_vertices-$density.txt" tests/test1.in >> test_outputs.txt
	done
done