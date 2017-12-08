import copy

def three_color(graph):
    nums = []
    combos = []
    output = []
    for i in range(len(graph)):
        nums.append(0)

    three_color_recursive(len(graph), combos, nums)

    for i in range(len(combos)):
        node_num = 0
        combination = {}
        for node in graph:
            combination[node] = combos[i][node_num]
            # output.append({node: combos[i][node_num]})
            node_num = node_num + 1
        output.append(combination)

    print (len(output))
    return output

def three_color_recursive(n, combinations, nums):
    if n < 1:
        combinations.append(copy.deepcopy(nums))
    else:
        nums[n-1] = 1
        three_color_recursive(n-1, combinations, nums)
        nums[n-1] = 2
        three_color_recursive(n-1, combinations, nums)
        nums[n-1] = 3
        three_color_recursive(n-1, combinations, nums)

print(three_color({"A" : ["B"], "B" : ["A"], "C": ["A"], "D": ["A"], "E": ["B"], "F": ["E"]}))