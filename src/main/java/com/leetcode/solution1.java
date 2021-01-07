package com.leetcode;

import java.util.*;
import java.util.function.DoubleConsumer;

/**
 * @author:yaoshuai.1024
 * @date: 2021/1/4 10:09 下午
 */
public class solution1 {

    public static void main(String[] args) {
        List<List<String>> equations = new ArrayList<>();
        List<String> equations1 = Arrays.asList("a","b");
        List<String> equations2 = Arrays.asList("b","c");
        equations.add(equations1);
        equations.add(equations2);

        double[] values = new double[]{2.0,3.0};

        List<List<String>> queries = new ArrayList<>();
        queries.add(Arrays.asList("a","c"));
        queries.add(Arrays.asList("b","a"));
        queries.add(Arrays.asList("a","e"));
        queries.add(Arrays.asList("a","a"));
        queries.add(Arrays.asList("x","x"));

        double[] doubles = calcEquation(equations, values, queries);
        Arrays.stream(doubles).forEach(System.out::println);
    }


    public static double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        int nvars = 0;
        Map<String, Integer> variables = new HashMap<String, Integer>();

        int n = equations.size();
        for (int i = 0; i < n; i++) {
            if (!variables.containsKey(equations.get(i).get(0))) {
                variables.put(equations.get(i).get(0), nvars++);
            }
            if (!variables.containsKey(equations.get(i).get(1))) {
                variables.put(equations.get(i).get(1), nvars++);
            }
        }

        // 对于每个点，存储其直接连接到的所有点及对应的权值
        List<Pair>[] edges = new List[]{new ArrayList<Pair>(),new ArrayList<Pair>(),new ArrayList<Pair>()};
//        Arrays.stream(edges).forEach(a -> a.add(new Pair(1,1.0));
//        for (int i = 0; i < nvars; i++) {
//            edges[i] = new ArrayList<Pair>();
//        }
        for (int i = 0; i < n; i++) {
            int va = variables.get(equations.get(i).get(0)), vb = variables.get(equations.get(i).get(1));
            edges[va].add(new Pair(vb, values[i]));
            edges[vb].add(new Pair(va, 1.0 / values[i]));
        }

        int queriesCount = queries.size();
        double[] ret = new double[queriesCount];
        for (int i = 0; i < queriesCount; i++) {
            List<String> query = queries.get(i);
            double result = -1.0;
            if (variables.containsKey(query.get(0)) && variables.containsKey(query.get(1))) {
                int ia = variables.get(query.get(0)), ib = variables.get(query.get(1));
                if (ia == ib) {
                    result = 1.0;
                } else {
                    Queue<Integer> points = new LinkedList<Integer>();
                    points.offer(ia);
                    double[] ratios = new double[nvars];
                    Arrays.fill(ratios, -1.0);
                    ratios[ia] = 1.0;

                    while (!points.isEmpty() && ratios[ib] < 0) {
                        int x = points.poll();
                        for (Pair pair : edges[x]) {
                            int y = pair.index;
                            double val = pair.value;
                            if (ratios[y] < 0) {
                                ratios[y] = ratios[x] * val;
                                points.offer(y);
                            }
                        }
                    }
                    result = ratios[ib];
                }
            }
            ret[i] = result;
        }
        return ret;
    }

}

class Pair {
    int index;
    double value;

    Pair(int index, double value) {
        this.index = index;
        this.value = value;
    }
}