#include <stdio.h>
#include <iostream>
#include <vector>

using namespace std;

class SegmentTree {
private:
	vector<int> m, d;

	void build(int ind, int l, int r) {
		if (l == r) {
			m[ind] = l;
		}
		else {
			build(2 * ind, l, (l + r) / 2);
			build(2 * ind + 1, (l + r + 2) / 2, r);
			m[ind] = d[m[2 * ind]] < d[m[2 * ind + 1]] ? m[2 * ind] : m[2 * ind + 1];
		}
	}

	bool inside(int a, int b, int l, int r) {
		return (a >= l && b <= r);
	}

	bool outside(int a, int b, int l, int r) {
		return (a > r || b < l);
	}

	int rmq(int i, int a, int b, int l, int r) {
		if (inside(a, b, l, r)) return m[i];
		int rmql = outside(a, (a + b) / 2, l, r) ? -1 : rmq(2 * i, a, (a + b) / 2, l, r);
		int rmqr = outside((a + b + 2) / 2, b, l, r) ? -1 : rmq(2 * i + 1, (a + b + 2) / 2, b, l, r);
		return rmql == -1 ? rmqr : rmqr == -1 ? rmql : d[rmql] < d[rmqr] ? rmql : rmqr;
	}

public:
	SegmentTree(const vector<int> &data) {
		d = data;
		for (int i = 0; i < 4 * d.size(); i++) {
			m.push_back(0);
		}
		build(1, 0, d.size() - 1);
	}

	int rmq(int l, int r) {
		return rmq(1, 0, d.size() - 1, l, r);
	}
};