# discovery
This repo contains a microservice implementation of the shortest path algorithm using Djikstra and realtion DB ie. DERBY and spring data JPA with hibernate for data persistence. This repo also contains soap and rest implemenation for querying the shortest paths. The allowed shortest paths source and destination are currently given below as:
A
B
C
D
E
F
G
H
I
J
K
L
M
N
O
P
Q
R
S
T
U
V
W
X
Y
Z
A'
B'
C'
D'
E'
F'
G'
H'
I'
J'
K'
L'
The output will be a "->" separated series of nodes starting at source and ending at destination For Eg. A->D-M.

To Test the application:

1. Do a mvn clean install to generate the boot jar.

2. Run java -jar <Genereated jar file> from command line.

3. Use rest client like Postman or soap client like SOAP UI
to test the application

Note: Input valid value does not return a proper message right now,
that's got to change in future. Please bear with me.


