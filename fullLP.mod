param nVertex;
param nResource;
param nStation;
param startVertices {1..nStation};
param graph {1..nVertex, 1..nVertex};
param values {1..nVertex};
param attRoute1 {1..1, 1..2};
param attRoute2 {1..1, 1..2};
param attRoute3 {1..2, 1..2};
param attRoute4 {1..1, 1..2};
param routStatus1{1..1};
param routStatus2{1..1};
param routStatus3{1..2};
param routStatus4{1..1};
var cov {1..nVertex, 1..nVertex} >= 0;
var U;
maximize objective: U;
subject to
bound1:
sum {i in 1..nStation} cov[1, startVertices[i]] = nResource;
bound2{i in 2..nVertex-1}:
sum {j in 1..(i-1)} cov[j, i] - sum {j in (i+1)..nVertex} cov[i, j] = 0;
bound3{i in 1..nVertex, j in 1..nVertex}:
cov[i, j] <= graph[i, j];
bound4:
U <= -((1 - sum {i in 1..1} cov[attRoute1[i, 1], attRoute1[i, 2]]*routStatus1[i])*values[3]);
bound5:
U <= -((1 - sum {i in 1..1} cov[attRoute2[i, 1], attRoute2[i, 2]]*routStatus2[i])*values[5]);
bound6:
U <= -((1 - sum {i in 1..2} cov[attRoute3[i, 1], attRoute3[i, 2]]*routStatus3[i])*values[6]);
bound7:
U <= -((1 - sum {i in 1..1} cov[attRoute4[i, 1], attRoute4[i, 2]]*routStatus4[i])*values[6]);
