#include <stdio.h>
#include <iostream>
#include <vector>
#include <map>

using namespace std;

template <class T> class UnionFind {
private:
	map<T, T> e; // Maps elements to their parents
	map<T, int> r; // Maps elements to their ranks

public:
	UnionFind(const vector<T> &items) {
		for (const T &item : items) {
			e[item] = item;
			r[item] = 0;
		}
	}

	T findParent(T x) {
		if (e.find(x) == e.end()) throw std::invalid_argument("Element not found");
		T par = e.find(x)->second;
		return (par == x ? x : e[x] = findParent(par));
	}

	bool sameSet(T x, T y) {
		return findParent(x) == findParent(y);
	}

	void merge(T x, T y) {
		if (sameSet(x, y)) return;
		T p = findParent(x), q = findParent(y);
		int rp = r.find(p)->second, rq = r.find(q)->second;
		if (rp > rq) e[q] = p;
		else if (rq > rp) e[p] = q;
		else { e[p] = q; r[q]++; }
	}

	void print() {
		auto it = e.begin(), itr = r.begin();
		while (it != e.end()) {
			cout << "element: " << it->first << " parent: " << it->second << " rank: " << itr->second << endl;
			it++; itr++;
		}
	}
};