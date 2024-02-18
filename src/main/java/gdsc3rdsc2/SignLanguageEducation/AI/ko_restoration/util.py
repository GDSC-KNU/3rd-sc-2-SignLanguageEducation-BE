def create_set(inputFile):
    return_list = list()
    with open(inputFile, "rt", errors = 'ignore') as f:
        for line in f.readlines():
            return_list.append(line.strip())
    return set(return_list)
