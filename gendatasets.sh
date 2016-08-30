# 0.5 density sets
echo "Generating all data sets..."

for density in $(seq 0.5 0.5 2.5)
do
	for n_vertices in {1000..3000..500}
	do
		python27 gendata.py $n_vertices $density "data-$n_vertices-$density.txt"
	done
done