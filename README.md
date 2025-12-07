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

Image of API Design: ![API_Design](https://github.com/CPS353-Suny-New-Paltz/project-starter-code-todarom3/blob/main/src/project/annotations/API_Design.jpg?raw=true)
