# Method that does a depth-first search algorithm on a tree.
def DFS(tree):

    # Initializing a visited list to keep track of vertices.
    visited = []

    # Choosing a separate, recursive function
    # because it generally made things easier.
    recursive_DFS(tree, "A", visited)

    return visited

# Helper method that actually executes the DFS
def recursive_DFS(tree, start, visited):

    # Simply, just traverse the left-most child
    # every iteration.
    if start not in visited:
        visited.append(start)
    for node in tree[start]:
        recursive_DFS(tree, node, visited)

# Method that does a breadth-first search on a valid tree.
def BFS(tree):

    # Initializing a visited list to keep track of vertices.
    visited = []

    # Choosing a separate, recursive function
    # because it generally made things easier.
    recursive_BFS(tree, "A", visited)

    return visited

# Helper method that actually executes the breadth-first search.
def recursive_BFS(tree, start, visited):

    # We start at the starting node, and get all their
    # children from left-to right,
    # and do this for every node.
    if start not in visited:
        visited.append(start)

    for node in tree[start]:
        visited.append(node)

    for node in tree[start]:
        recursive_BFS(tree, node, visited)

# Method that returns all edges in non-decreasing order.
def edge_get(graph):

    # Initializing our edge list.
    edge_list = []
    for node in graph:
        for neighbor in graph[node]:

            # We test both possible edges [A, B] [B, A]
            # to see if they are members of the edge set.
            # The key here to remember is that we designed
            # an array to be constructed like [weight, [edge]]
            possible_edge_1 = [neighbor[1], [node, neighbor[0]]]
            possible_edge_2 = [neighbor[1], [neighbor[0], node]]
            if possible_edge_1 not in edge_list and possible_edge_2 not in edge_list:
                edge_list.append(possible_edge_1)

    # One simple line that sorts our edges by the weight!
    edge_list.sort(key=sortByFirstE)

    # Simply just making a set out of edge list
    # to eliminate duplicates.
    new_edge_list = []
    for edge in edge_list:
        new_edge_list.append(edge[1])

    return new_edge_list

# Helper method that sorts elements by their first index.
def sortByFirstE(element):
    return element[0]

# Method that performs Kruskal's algorithm on a graph.
def min_kruskal(graph):

    # Initializing our output variables.
    output = []
    mst = {}
    for edge in edge_get(graph):
        test_mst = mst

        # Essentially, in our non-decreasing edges, we
        # add them one by one to our set that doesn't already
        # contain them.
        if edge[0] not in test_mst:
            test_mst.setdefault(edge[0], list()).append(edge[1])
            test_mst.setdefault(edge[1], list()).append(edge[0])
        else:
            test_mst.setdefault(edge[0], list()).append(edge[1])
            test_mst.setdefault(edge[1], list()).append(edge[0])

        # We create a copy of our previous MST before we
        # actually edit the real MST to check if the test
        # MST will contain a cycle.
        if not cycle_exists(test_mst):
            mst = test_mst
            output.append([edge[0], edge[1]])

    return output

# Method that performs Prim's algorithm on a graph.
def min_prim(graph):

    # Initializing our output variables, and our
    # vertex set to start at 'A'
    e = []
    v = ["A"]

    # Keep iterating until all our vertices are in our graph.
    while len(v) != len(graph):
        smallest_edges = []

        # First, find the smallest neighboring edge of EVERY
        # vertex.
        for vertex in v:
            edge = find_smallest(graph, vertex, e)
            smallest_edges.append([vertex, edge[0], edge[1]])

        # Now, compare all of the possible smallest
        # neighboring edges.
        smallest = ["", "", -1]
        for edge in smallest_edges:
            if smallest[2] == -1 or smallest[2] > edge[2]:
                if [edge[0], edge[1]] not in e and [edge[1], edge[0]] not in e:
                    smallest = edge

        # Append to our edge set the next smallest
        # available option.
        e.append([smallest[0], smallest[1]])
        if smallest[0] not in v:
            v.append(smallest[0])
        else:
            v.append(smallest[1])

    return e

# Helper method that finds the smallest neighboring edge
# given a graph, a vertex, and an edge set.
def find_smallest(graph, vertex, e):
    smallest = ["", -1]
    for node in graph[vertex]:
        if smallest[1] == -1 or smallest[1] > node[1]:
            if [vertex, node[0]] not in e and [node[0], vertex] not in e:
                smallest = node

    return smallest

# Helper method that determines if a graph has a cycle.
# This was a nightmare, but it works!
def cycle_exists(G):

    # Initializing our marked set with false values.
    marked = {u: False for u in G}
    found_cycle = [False]

    # For every vertex in our graph, trace the path using our
    # recursive helper method.
    for u in G:
        if not marked[u]:
            trace_path(G, u, found_cycle, u, marked)
        if found_cycle[0]:
            break
    return found_cycle[0]

# Helper method to trace the path in a given graph. Meant to
# be ran alongside the cycle_exists method.
def trace_path(G, u, found_cycle, pred_node, marked):

    # If we found a cycle, then stop!
    if found_cycle[0]:
        return

    # Current vertex has been visited.
    marked[u] = True

    for v in G[u]:
        if marked[v] and v != pred_node:
            found_cycle[0] = True
            return
        if not marked[v]:
            trace_path(G, v, found_cycle, u, marked)

# Just some tests to make sure the program output was the same from the sheet.
# print(DFS({"A": ["B", "C"], "B": ["D"], "C": [], "D": []}))
# print(DFS({"A" : ["B", "C", "D"], "B" : [], "C" : [], "D": []}))
#
# print(BFS({"A": ["B", "C"], "B": ["D"], "C": [], "D": []}))
# print(BFS({"A" : ["B", "C", "D"], "B" : [], "C" : [], "D": []}))
#
# print(edge_get({"A" : [["B", 10], ["D", 5]], "B" : [["A", 10], ["C", 5]], "C" : [["B", 5], ["D", 15]], "D" : [["C", 15], ["A", 5]]}))
# print(edge_get({"A" : [["B", 10], ["D", 5], ["C", 10]], "B" : [["A", 10], ["C", 5]], "C" : [["A", 10], ["B", 5], ["D", 15]], "D" : [["C", 15], ["A", 5]]}))
#
# print(min_kruskal({"A" : [["B", 10], ["D", 5]], "B" : [["A", 10], ["C", 5]], "C" : [["B", 5], ["D", 15]], "D" : [["C", 15], ["A", 5]]}))
# print(min_prim({"A" : [["B", 10], ["D", 5]], "B" : [["A", 10], ["C", 5]], "C" : [["B", 5], ["D", 15]], "D" : [["C", 15], ["A", 5]]}))