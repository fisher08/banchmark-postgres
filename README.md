## Benchmark for load whole data set from Postgres
```
Benchmark                              (y)  Mode  Cnt      Score      Error  Units
PerformancePostgresTest.performTest     10  avgt    5   2017.963 ±    6.910  ms/op
PerformancePostgresTest.performTest    100  avgt    5   2082.399 ±    5.336  ms/op
PerformancePostgresTest.performTest   1000  avgt    5   2632.361 ±  458.446  ms/op
PerformancePostgresTest.performTest  10000  avgt    5  13989.100 ± 2265.910  ms/op
PerformancePostgresTest.performTest  29000  avgt    5  74455.941 ± 6053.661  ms/op
```

Benchmark for Postgresql https://github.com/fisher08/banchmark-mongo