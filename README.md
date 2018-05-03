# Wikiview
This is a program that takes a wikipedia article, creates a data graph, and allows the user to fidn the shorted path between two pages.
This program uses a weighted graph, where the weighting is determined by cosine word frequency from each article, and uses Dijkstra's 
algorithm using those weights in a priority queue to find the shorted path. This program also shows the most similar page for each page 
in the path using cosine.
