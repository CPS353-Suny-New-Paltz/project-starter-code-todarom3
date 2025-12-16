The system will compute the number of prime numbers less than or equal to the input number.

Example - Input: N = 30
Output: Prime numbers ≤ 30: 2, 3, 5, 7, 11, 13, 17, 19, 23, 29
Total primes found: 10

Multi-Threaded Version (Checkpoint 6):

I added a multi-threaded version of my NetworkAPI called "UserComputeAPIMultiThreadedImpl".

It uses a fixed thread pool with 4 threads.  
I chose 4 because it's a reasonable number for most computers and has good parallel performance.

Each input number is processed on a separate thread, and the results are combined at the end.
Only the multi-user test uses this version; the regular tests still use the single-threaded one.

Performance Optimization and Benchmarking (Checkpoint 8)

I identified a CPU bottleneck in the compute engine by measuring the execution time for large inputs, which showed that repeated prime checks were inefficient. I fixed this by adding a second compute engine implementation using the Sieve of Eratosthenes, which computes primes more efficiently.

An integration benchmark comparing both versions is located at:
test/ComputeEngineBenchmarkIT.java
Here is the link to the pull request:
https://github.com/CPS353-Suny-New-Paltz/project-starter-code-todarom3/pull/54

For input N = 100,000, the optimized version was well over 10% faster than the original, meeting the performance requirement.

Image of API Design: ![API_Design](https://github.com/CPS353-Suny-New-Paltz/project-starter-code-todarom3/blob/main/src/project/annotations/API_Design.jpg?raw=true)
