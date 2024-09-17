package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;


public class Solution {
    Logger logger = Logger.getLogger(getClass().getName());
    int[][] directions = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

    public void log(String sth) {
        logger.info(sth);
    }

    // 1. Two Sum
    public int[] twoSum(int[] nums, int target) {
        int[] res = new int[2];
        int len = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < len; i++) {
            int other = target - nums[i];
            if (map.containsKey(other)) {
                res[0] = i;
                res[1] = map.get(other);
                return res;
            } else {
                map.put(nums[i], i);
            }
        }
        return res;
    }

    // 2. Add Two Numbers
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head1 = l1;
        ListNode head2 = l2;
        ListNode res = new ListNode();
        ListNode head = res;
        int p = 0;
        while (head1 != null || head2 != null || p != 0) {
            int temp = p;
            if (head1 != null) {
                temp += head1.val;
                head1 = head1.next;
            }
            if (head2 != null) {
                temp += head2.val;
                head2 = head2.next;
            }
            p = temp / 10;
            head.next = new ListNode(temp % 10);
            head = head.next;
        }
        return res.next;
    }

    // 129. Sum Root to Leaf Numbers
    private int sumNumbersDfs(TreeNode node, int sum) {
        if (node == null)
            return 0;
        sum *= 10;
        sum += node.val;
        if (node.left == null && node.right == null) {
            return sum;
        }
        return sumNumbersDfs(node.left, sum) + sumNumbersDfs(node.right, sum);
    }

    public int sumNumbers(TreeNode root) {
        return sumNumbersDfs(root, 0);
    }

    // 3. Longest Substring Without Repeating Characters
    public int lengthOfLongestSubstring(String s) {
        HashSet<Character> set = new HashSet<>();
        int head = 0;
        int res = 0;
        for (int sail = 0; sail < s.length(); sail++) {
            char cur = s.charAt(sail);
            while (set.contains(cur)) {
                set.remove(s.charAt(head));
                head++;
            }
            set.add(cur);
            res = Math.max(res, sail - head + 1);
        }
        return res;
    }

    // 623. Add One Row to Tree
    private void addOneRowDfs(TreeNode root, int depth, int aim, int val) {

        if (root == null)
            return;
        if (depth == aim - 1) {
            TreeNode leftNode = root.left;
            TreeNode rightNode = root.right;
            root.left = new TreeNode(val);
            root.left.left = leftNode;
            root.right = new TreeNode(val);
            root.right.right = rightNode;
        } else {
            addOneRowDfs(root.left, depth + 1, aim, val);
            addOneRowDfs(root.right, depth + 1, aim, val);
        }
    }

    public TreeNode addOneRow(TreeNode root, int val, int depth) {
        if (depth == 1) {
            TreeNode node = new TreeNode(val);
            node.left = root;
            return node;
        }
        addOneRowDfs(root, 1, depth, val);
        return root;
    }

    // 1287. Element Appearing More Than 25% In Sorted Array
    public int findSpecialInteger(int[] arr) {
        int len = arr.length;
        int range = len / 4;
        for (int i = 0; i + range < len; i++) {
            int right = i + range;
            if (arr[i] == arr[right]) {
                return arr[i];
            }
        }
        return -1;
    }

    // 988. Smallest String Starting From Leaf
    public void smallestFromLeafDfs(TreeNode node, StringBuilder res, StringBuilder path) {
        if (node == null)
            return;
        char temp = (char) ('a' + node.val);
        path.insert(0, temp);
        if (node.left == null && node.right == null) {
            String cur = path.toString();
            if (res.length() == 0 || cur.compareTo(res.toString()) < 0) {
                res.setLength(0);
                res.append(cur);
            }
        }
        smallestFromLeafDfs(node.left, res, path);
        smallestFromLeafDfs(node.right, res, path);
        path.deleteCharAt(0);
    }

    public String smallestFromLeaf(TreeNode root) {
        StringBuilder res = new StringBuilder();
        StringBuilder path = new StringBuilder();
        smallestFromLeafDfs(root, res, path);
        return res.toString();
    }

    // 992. Subarrays with K Different Integers
    public int subarraysWithKDistinct(int[] nums, int k) {
        int resMaxK = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        int l = 0;
        int r = 0;
        while (r < nums.length) {
            int cur = nums[r];
            map.put(cur, map.getOrDefault(cur, 0) + 1);
            while (map.keySet().size() > k) {
                int temp = nums[l];
                l++;
                map.put(temp, map.get(temp) - 1);
                if (map.get(temp) == 0) {
                    map.remove(temp);
                }
            }
            resMaxK += r - l + 1;
            r++;
        }
        int resMaxKs1 = 0;
        map = new HashMap<>();
        l = 0;
        r = 0;
        while (r < nums.length) {
            int cur = nums[r];
            map.put(cur, map.getOrDefault(cur, 0) + 1);
            while (map.keySet().size() >= k) {
                int temp = nums[l];
                l++;
                map.put(temp, map.get(temp) - 1);
                if (map.get(temp) == 0) {
                    map.remove(temp);
                }
            }
            resMaxKs1 += r - l + 1;
            r++;
        }
        return resMaxK - resMaxKs1;
    }

    // 200. Number of Islands
    public void numIslandsDfs(char[][] grid, int x, int y) {
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) {
            return;
        }
        if (grid[x][y] == '1') {
            grid[x][y] = '0';
            numIslandsDfs(grid, x - 1, y);
            numIslandsDfs(grid, x + 1, y);
            numIslandsDfs(grid, x, y - 1);
            numIslandsDfs(grid, x, y + 1);
        }
    }

    public int numIslands(char[][] grid) {
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    res++;
                    numIslandsDfs(grid, i, j);
                }
            }
        }
        return res;
    }

    // 872. Leaf-Similar Trees
    public boolean isLeaf(TreeNode node) {
        return node.left == null && node.right == null;
    }

    public void leafSimilarDfs(TreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        if (isLeaf(node)) {
            list.add(node.val);
        }
        leafSimilarDfs(node.left, list);
        leafSimilarDfs(node.right, list);
    }

    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        List<Integer> leaf1 = new ArrayList<>();
        List<Integer> leaf2 = new ArrayList<>();
        leafSimilarDfs(root1, leaf1);
        leafSimilarDfs(root2, leaf2);
        return leaf1.equals(leaf2);
    }

    // 463. Island Perimeter
    public int islandPerimeter(int[][] grid) {
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0) {
                    continue;
                }
                if (i == 0 || grid[i - 1][j] == 0) {
                    res++;
                }
                if (i == grid.length - 1 || grid[i + 1][j] == 0) {
                    res++;
                }
                if (j == 0 || grid[i][j - 1] == 0) {
                    res++;
                }
                if (j == grid[0].length - 1 || grid[i][j + 1] == 0) {
                    res++;
                }
            }
        }
        return res;
    }

    // 1992. Find All Groups of Farmland
    private int[] findFarmlandDfs(int[][] land, Set<Integer> set, int i, int j) {
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[] { i, j });
        set.add(i * 1000 + j);
        int minX = i, minY = j, maxX = i, maxY = j;
        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            int cx = current[0], cy = current[1];
            for (int[] dir : directions) {
                int nx = cx + dir[0], ny = cy + dir[1];
                if (nx >= 0 && nx < land.length && ny >= 0 && ny < land[0].length
                        && land[nx][ny] == 1 && !set.contains(nx * 1000 + ny)) {
                    set.add(nx * 1000 + ny);
                    stack.push(new int[] { nx, ny });
                    minX = Math.min(minX, nx);
                    minY = Math.min(minY, ny);
                    maxX = Math.max(maxX, nx);
                    maxY = Math.max(maxY, ny);
                }
            }
        }
        return new int[] { minX, minY, maxX, maxY };
    }

    public int[][] findFarmland(int[][] land) {
        int rows = land.length;
        int cols = land[0].length;
        Set<Integer> visited = new HashSet<>();
        List<int[]> result = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int temp = i * 1000 + j;
                if (land[i][j] == 1 && !visited.contains(temp)) {
                    int[] bounds = findFarmlandDfs(land, visited, i, j);
                    result.add(bounds);
                }
            }
        }
        return result.toArray(new int[result.size()][]);
    }

    // 131. Palindrome Partitioning
    public boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) == s.charAt(right)) {
                left++;
                right--;
            } else {
                return false;
            }
        }
        return true;
    }

    private void partitionFindPalindrome(String s, int left, List<List<String>> res, List<String> curList) {
        if (left >= s.length()) {
            res.add(new ArrayList<>(curList));
            return;
        }
        for (int i = left; i < s.length(); i++) {
            String cur = s.substring(left, i + 1);
            if (isPalindrome(cur)) {
                curList.add(cur);
                partitionFindPalindrome(s, i + 1, res, curList);
                curList.remove(curList.size() - 1);
            }
        }
    }

    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        partitionFindPalindrome(s, 0, res, new ArrayList<>());
        return res;
    }

    // 5. Longest Palindromic Substring
    public String longestPalindrome(String s) {
        int len = s.length();
        if (len < 2)
            return s;
        int max = 0;
        String res = "";
        boolean[][] canCut = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j <= i; j++) {
                if (s.charAt(i) == s.charAt(j) && (i - j <= 1 || canCut[j + 1][i - 1])) {
                    canCut[j][i] = true;
                    if (i - j + 1 > max) {
                        max = i - j + 1;
                        res = s.substring(j, i + 1);
                    }
                }
            }
        }
        return res;
    }

    // 132. Palindrome Partitioning II
    public int minCut(String s) {
        int len = s.length();
        int[] cuts = new int[len];
        boolean[][] canCut = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            int minCut = i;
            for (int j = 0; j <= i; j++) {
                if (s.charAt(i) == s.charAt(j) && (i - j <= 1 || canCut[j + 1][i - 1])) {
                    canCut[j][i] = true;
                    if (j == 0) {
                        minCut = 0;
                        continue;
                    }
                    minCut = Math.min(minCut, cuts[j - 1] + 1);
                }
            }
            cuts[i] = minCut;
        }
        return cuts[len - 1];
    }

    // 1404. Number of Steps to Reduce a Number in Binary Representation to One
    public int numSteps(String s) {
        int res = 0;
        int carry = 0;
        for (int i = s.length() - 1; i > 0; i--) {
            char ch = s.charAt(i);
            if (ch - '0' + carry == 1) {
                carry = 1;
                res += 2;
            } else {
                res += 2;
            }
        }
        return res + carry;
    }

    // 1894. Find the Student that Will Replace the Chalk
    public int chalkReplacer(int[] chalk, int k) {
        int n = chalk.length;
        int[] newChalk = new int[n];
        newChalk[0] = chalk[0];
        for (int i = 0; i < n - 1; i++) {
            if (newChalk[i] > k) {
                return i;
            }
            newChalk[i + 1] = chalk[i + 1] + newChalk[i];
        }
        if (newChalk[n - 1] > k) {
            return n - 1;
        }
        k %= newChalk[n - 1];
        for (int i = 0; i < n; i++) {
            if (newChalk[i] > k) {
                return i;
            }
        }
        return 0;
    }

    // 1391. Check if There is a Valid Path in a Grid
    public boolean hasValidPath(int[][] grid) {
        int[][][] dirs = {
                { { 0, -1 }, { 0, 1 } },
                { { -1, 0 }, { 1, 0 } },
                { { 0, -1 }, { 1, 0 } },
                { { 0, 1 }, { 1, 0 } },
                { { 0, -1 }, { -1, 0 } },
                { { 0, 1 }, { -1, 0 } }
        };
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[] { 0, 0 });
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        visited[0][0] = true;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int i = cur[0], j = cur[1];
            for (int[] dir : dirs[grid[i][j] - 1]) {
                int newi = i + dir[0], newj = j + dir[1];
                if (newi >= 0 && newj >= 0 && newi < m && newj < n && !visited[newi][newj]) {
                    for (int[] dir2 : dirs[grid[newi][newj] - 1]) {
                        if (newi + dir2[0] == i && newj + dir2[1] == j) {
                            visited[newi][newj] = true;
                            queue.offer(new int[] { newi, newj });
                        }
                    }
                }
            }
        }
        return visited[m - 1][n - 1];
    }

    // 26. Remove Duplicates from Sorted Array
    public int removeDuplicates(int[] nums) {
        int k = 0;
        for (int i = 0; i < nums.length; i++) {
            while (i != nums.length - 1 && nums[i + 1] == nums[i]) {
                i++;
            }
            nums[k] = nums[i];
            k++;
        }
        return k;
    }

    // 1945. Sum of Digits of String After Convert
    public int getLucky(String s, int k) {
        int res = 0;
        for (char ch : s.toCharArray()) {
            int temp = ch - 'a' + 1;
            if (temp >= 10) {
                res += temp / 10;
                res += temp % 10;
            } else {
                res += temp;
            }
        }
        while (k != 1) {
            k--;
            int tempRes = res;
            res = 0;
            while (tempRes != 0) {
                res += tempRes % 10;
                tempRes /= 10;
            }
        }
        return res;
    }

    // 2750. Ways to Split Array Into Good Subarrays
    public int numberOfGoodSubarraySplits(int[] nums) {
        int n = nums.length;
        long res = 1;
        int zero = 0, one = 0;
        int i = 0;
        while (i < n) {
            if (nums[i] == 0) {
                zero++;
            } else {
                one++;
                if (one > 1 && zero >= 1) {
                    res *= zero + 1;
                    res %= 1e9 + 7;
                }
                zero = 0;
            }
            i++;
        }
        if (one == 0) {
            return 0;
        }
        return (int) (res);
    }

    // 2708. Maximum Strength of a Group
    public long maxStrength(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        long res = 1;
        boolean flag = false;
        int minus = 0;
        int notZero = 0;
        int min = -10;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                flag = true;
                res *= nums[i];
                notZero++;
            }
            if (nums[i] < 0) {
                minus++;
                if (min < nums[i]) {
                    min = nums[i];
                }
            }
        }
        if (!flag) {
            return 0;
        }
        if ((minus & 1) == 1) {
            if (minus == 1 && notZero == 1) {
                return 0;
            }
            res /= min;
        }
        return res;
    }

    // 874. Walking Robot Simulation
    public int robotSim(int[] commands, int[][] obstacles) {
        int[][] directions = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
        int res = 0;
        int x = 0, y = 0, dir = 0;
        for (int command : commands) {
            if (command == -1) {
                dir++;
                dir %= 4;
            } else if (command == -2) {
                dir += 3;
                dir %= 4;
            } else {
                for (int i = 0; i < command; i++) {
                    int nx = x + directions[dir][0];
                    int ny = y + directions[dir][1];
                    boolean stuck = false;
                    for (int j = 0; j < obstacles.length; j++) {
                        if (nx == obstacles[j][0] && ny == obstacles[j][1]) {
                            stuck = true;
                            break;
                        }
                    }
                    if (!stuck) {
                        x = nx;
                        y = ny;
                        res = Math.max(res, nx * nx + ny * ny);
                    }
                }
            }
        }
        return res;
    }

    // 633. Sum of Square Numbers
    public boolean judgeSquareSum(int c) {
        if (c == 0)
            return true;
        int i = 0, j = (int) Math.sqrt(c);
        if (c / j == j && c % j == 0) {
            return true;
        }
        while (i <= j) {
            long tempI = i * i;
            long tempJ = j * j;
            long temp = tempI + tempJ;
            if (temp == c) {
                return true;
            } else if (temp < c) {
                i++;
            } else {
                j--;
            }
        }
        return false;
    }

    // 7. Reverse Integer
    public int reverse(int x) {
        int res = 0;
        while (x != 0) {
            int temp = x % 10;
            x /= 10;
            if (Math.abs(x) == 0 && ((res > Integer.MAX_VALUE / 10 || (res == Integer.MAX_VALUE / 10 && temp > 7)) ||
                    (res < Integer.MIN_VALUE / 10 || (res == Integer.MIN_VALUE / 10 && temp < -8)))) {
                return 0;
            }

            res *= 10;
            res += temp;
        }
        return res;
    }

    // 2028. Find Missing Observations
    public int[] missingRolls(int[] rolls, int mean, int n) {
        int m = rolls.length;
        int sum = (m + n) * mean;
        int nSum = 0;
        for (int i = 0; i < m; i++) {
            nSum += rolls[i];
        }
        int subSum = sum - nSum;
        if (subSum >= n && subSum <= 6 * n) {
            int mm = subSum / n;
            int[] res = new int[n];
            for (int i = 0; i < n; i++) {
                res[i] = mm;
            }
            int minus = subSum - mm * n;
            if (minus == 0) {
                return res;
            } else if (minus < 0) {
                int temp = 0;
                while (minus < -(mm - 1)) {
                    res[temp] = 1;
                    minus += mm - 1;
                    temp++;
                }
                res[temp] += minus;
            } else {
                int temp = 0;
                while (minus > 6 - mm) {
                    res[temp] = 6;
                    minus += mm - 6;
                    temp++;
                }
                res[temp] += minus;
            }
            return res;
        } else {
            return new int[0];
        }
    }

    // 2053. Kth Distinct String in an Array
    public String kthDistinct(String[] arr, int k) {
        int len = arr.length;
        HashSet<String> set = new HashSet<>();
        HashSet<String> dup = new HashSet<>();
        for (int i = 0; i < len; i++) {
            if (set.contains(arr[i])) {
                dup.add(arr[i]);
            } else {
                set.add(arr[i]);
            }
        }
        for (int i = 0; i < len; i++) {
            if (!dup.contains(arr[i])) {
                k--;
                if (k == 0) {
                    return arr[i];
                }
            }
        }
        return "";
    }

    // 344. Reverse String
    public void reverseString(char[] s) {
        int i = 0, j = s.length - 1;
        while (i < j) {
            char ii = s[i], jj = s[j];
            s[j] = ii;
            s[i] = jj;
            i++;
            j--;
        }
    }

    // 3217. Delete Nodes From Linked List Present in Array
    public ListNode modifiedList(int[] nums, ListNode head) {
        ListNode dammyHead = new ListNode();
        HashSet set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        ListNode p = head;
        ListNode resP = dammyHead;
        while (p != null) {
            if (!set.contains(p.val)) {
                resP.next = p;
                resP = p;
            }
            p = p.next;
        }
        resP.next = null;
        return dammyHead.next;
    }

    // 1367. Linked List in Binary Tree
    public boolean isSubPath(ListNode head, TreeNode root) {
        return isSubPathDFS(head, head, root);
    }

    public boolean isSubPathDFS(ListNode head, ListNode cur, TreeNode curTree) {
        if (cur == null) {
            return true;
        }
        if (curTree == null) {
            return false;
        }
        if (curTree.val == cur.val) {
            cur = cur.next;
        } else if (head.val == curTree.val) {
            head = head.next;
        } else {
            cur = head;
        }
        return isSubPathDFS(head, cur, curTree.left) || isSubPathDFS(head, cur, curTree.right);
    }

    // 98. Validate Binary Search Tree
    public boolean isValidBST(TreeNode root) {
        return isValidBSTDFS(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    boolean isValidBSTDFS(TreeNode root, long min, long max) {
        if (root == null)
            return true;
        if (root.val <= min || root.val >= max) {
            return false;
        } else {
            return isValidBSTDFS(root.left, min, (long) root.val) && isValidBSTDFS(root.right, (long) root.val, max);
        }
    }

    // 1325. Delete Leaves With a Given Value
    public TreeNode removeLeafNodes(TreeNode root, int target) {
        if (root == null) {
            return null;
        }
        root.left = removeLeafNodes(root.left, target);
        root.right = removeLeafNodes(root.right, target);
        if (root.val == target && root.left == null && root.right == null) {
            return null;
        }
        return root;
    }

    // 456. 132 Pattern
    public boolean find132pattern(int[] nums) {
        int len = nums.length;
        if (len < 3)
            return false;
        int min = Integer.MIN_VALUE;
        // stack记录第二大数字值的堆栈，min记录第二大数字值
        Stack<Integer> stack = new Stack<>();
        for (int i = len - 1; i >= 0; i--) {
            if (nums[i] < min) {
                return true;
            }
            while (!stack.isEmpty() && nums[i] > stack.peek()) {
                min = stack.pop();
            }
            stack.push(nums[i]);
        }
        return false;
    }

    // 725. Split Linked List in Parts
    public ListNode[] splitListToParts(ListNode head, int k) {
        int len = 0;
        ListNode p = head;
        while (p != null) {
            p = p.next;
            len++;
        }
        ListNode[] res = new ListNode[k];
        int[] resLen = new int[k];
        int avg = len / k;
        int diff = len - k * avg;
        for (int i = 0; i < k; i++) {
            if (diff > 0) {
                resLen[i] = avg + 1;
                diff--;
            } else {
                resLen[i] = avg;
            }
        }
        ListNode cur = head;
        int curI = 0;
        int curLen = 0;
        while (cur != null) {
            if (resLen[curI] == 0) {
                break;
            }
            if (curLen == 0) {
                res[curI] = cur;
            }
            curLen++;
            ListNode temp = cur.next;
            if (resLen[curI] == curLen) {
                curI++;
                curLen = 0;
                if (cur.next != null) {
                    cur.next = null;
                }
            }
            cur = temp;
        }
        return res;
    }

    // 2022. Convert 1D Array Into 2D Array
    public int[][] construct2DArray(int[] original, int m, int n) {
        int len = original.length;
        if (len != m * n) {
            return new int[0][0];
        }
        int[][] res = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = original[i * n + j];
            }
        }
        return res;
    }

    // 2446. Determine if Two Events Have Conflict
    public boolean haveConflict(String[] event1, String[] event2) {
        int s1 = haveConflictTime(event1[0]);
        int e1 = haveConflictTime(event1[1]);
        int s2 = haveConflictTime(event2[0]);
        int e2 = haveConflictTime(event2[1]);
        return (s1 <= s2 && s2 <= e1) || (s2 <= s1 && s1 <= e2);
    }

    public int haveConflictTime(String s) {
        String num = s.substring(0, 2) + s.substring(3, 5);
        return Integer.parseInt(num);
    }

    // 2326. Spiral Matrix IV
    public int[][] spiralMatrix(int m, int n, ListNode head) {
        int[][] res = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = -1;
            }
        }
        boolean[][] visited = new boolean[m][n];
        ListNode cur = head;
        int i = 0, j = 0;
        int newI = 0;
        int newJ = 0;
        int dir = 0;
        int[][] directions = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
        visited[0][0] = true;
        res[0][0] = cur.val;
        cur = cur.next;
        while (cur != null) {
            int val = cur.val;
            cur = cur.next;
            newI = i + directions[dir][0];
            newJ = j + directions[dir][1];
            while (newI < 0 || newJ < 0 || newI >= m || newJ >= n || visited[newI][newJ]) {
                dir += 1;
                dir %= 4;
                newI = i + directions[dir][0];
                newJ = j + directions[dir][1];
            }
            i = newI;
            j = newJ;
            visited[newI][newJ] = true;
            res[newI][newJ] = val;
        }
        return res;
    }

    // 310. Minimum Height Trees
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if (n == 1)
            return new ArrayList<Integer>(List.of(0));
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            List l0 = map.getOrDefault(edge[0], new ArrayList<Integer>());
            l0.add(edge[1]);
            List l1 = map.getOrDefault(edge[1], new ArrayList<Integer>());
            l1.add(edge[0]);
            map.put(edge[0], l0);
            map.put(edge[1], l1);
        }
        int[] degrees = new int[n];
        Queue<Integer> leaves = new LinkedList<>();
        for (int key : map.keySet()) {
            degrees[key] = map.get(key).size();
            if (degrees[key] == 1) {
                leaves.add(key);
            }
        }
        List<Integer> roots = new ArrayList<>();
        int nodes = n;
        while (nodes > 2) {
            int leavesNum = leaves.size();
            nodes -= leavesNum;
            for (int i = 0; i < leavesNum; i++) {
                int cur = leaves.poll();
                for (int neighbor : map.get(cur)) {
                    degrees[neighbor]--;
                    if (degrees[neighbor] == 1) {
                        leaves.add(neighbor);
                    }
                }
            }
        }
        roots.addAll(leaves);
        return roots;
    }

    // 830. Positions of Large Groups
    public List<List<Integer>> largeGroupPositions(String s) {
        List<List<Integer>> res = new ArrayList<>();
        int cur = 0, start = 0;
        char last = '0';
        for (int i = 0; i < s.length(); i++) {
            if (last == s.charAt(i)) {
                cur++;
            } else {
                if (cur > 2) {
                    List<Integer> temp = new ArrayList<>();
                    temp.add(start);
                    temp.add(i - 1);
                    res.add(temp);
                }
                start = i;
                cur = 1;
                last = s.charAt(i);
            }
        }
        if (cur > 2) {
            List<Integer> temp = new ArrayList<>();
            temp.add(start);
            temp.add(start + cur - 1);
            res.add(temp);
        }
        return res;
    }

    // 783. Minimum Distance Between BST Nodes
    public int minDiffInBST(TreeNode root) {
        int res = Integer.MAX_VALUE;
        List<Integer> list = new ArrayList<>();
        minDiffInBSTHelper(root, list);
        Collections.sort(list);
        int[] array = list.stream().mapToInt(Integer::intValue).toArray();
        for (int i = 0; i < array.length - 1; i++) {
            res = Math.min(res, array[i + 1] - array[i]);
        }
        return res;
    }

    public void minDiffInBSTHelper(TreeNode root, List<Integer> res) {
        if (root == null)
            return;
        res.add(root.val);
        minDiffInBSTHelper(root.left, res);
        minDiffInBSTHelper(root.right, res);
    }

    // 2807. Insert Greatest Common Divisors in Linked List
    public ListNode insertGreatestCommonDivisors(ListNode head) {
        ListNode p = head;
        ListNode pre = head;
        p = p.next;
        while (p != null) {
            int v1 = p.val;
            int preV = pre.val;
            while (v1 != 0) {
                int temp = v1;
                v1 = preV % v1;
                preV = temp;
            }
            ListNode node = new ListNode(preV);
            pre.next = node;
            node.next = p;
            pre = p;
            p = p.next;
        }
        return head;
    }

    // 1038. Binary Search Tree to Greater Sum Tree
    int bstToGstRes = 0;

    public TreeNode bstToGst(TreeNode root) {
        if (root == null)
            return root;
        bstToGst(root.right);
        bstToGstRes += root.val;
        root.val = bstToGstRes;
        bstToGst(root.left);
        return root;
    }

    // 1971. Find if Path Exists in Graph
    public boolean validPath(int n, int[][] edges, int source, int destination) {
        boolean[] visited = new boolean[n];
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            List list0 = map.getOrDefault(edge[0], new ArrayList<>());
            list0.add(edge[1]);
            map.put(edge[0], list0);
            List list1 = map.getOrDefault(edge[1], new ArrayList<>());
            list1.add(edge[0]);
            map.put(edge[1], list1);
        }
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            if (cur == destination) {
                return true;
            }
            for (int neighbor : map.get(cur)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
        return false;
    }

    // 2220. Minimum Bit Flips to Convert Number
    public int minBitFlips(int start, int goal) {
        int res = 0;
        int step = start ^ goal;
        while (step != 0) {
            res += step & 1;
            step >>= 1;
        }
        return res;
    }

    // 2816. Double a Number Represented as a Linked List
    public ListNode doubleIt(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode reverseList = doubleItReverse(head);
        ListNode p = reverseList;
        ListNode pre = null;
        int carry = 0;
        while (p != null) {
            int t = 2 * p.val + carry;
            p.val = t % 10;
            carry = t / 10;
            pre = p;
            p = p.next;
        }
        if (carry != 0) {
            ListNode extra = new ListNode(carry);
            pre.next = extra;
        }
        return doubleItReverse(reverseList);
    }

    public ListNode doubleItReverse(ListNode head) {
        ListNode pre = null;
        ListNode p = head;
        while (p != null) {
            ListNode temp = p.next;
            p.next = pre;
            pre = p;
            p = temp;
        }
        return pre;
    }

    // 2096. Step-By-Step Directions From a Binary Tree Node to Another
    public String getDirections(TreeNode root, int startValue, int destValue) {
        StringBuilder startStr = new StringBuilder();
        StringBuilder endStr = new StringBuilder();
        getDirectionsBfs(startValue, root, startStr);
        getDirectionsBfs(destValue, root, endStr);
        // 需要对root特殊处理
        int max = Math.min(startStr.length(), endStr.length());
        int i = 0;
        while (i < max && startStr.charAt(startStr.length() - 1 - i) == endStr.charAt(endStr.length() - 1 - i)) {
            i++;
        }
        StringBuilder resStr = new StringBuilder();
        for (int j = 0; j < startStr.length() - i; j++) {
            resStr.append('U');
        }
        String leftString = endStr.reverse().substring(i);
        resStr.append(leftString);
        return resStr.toString();
    }

    public boolean getDirectionsBfs(int v, TreeNode node, StringBuilder sb) {
        if (node.val == v) {
            return true;
        }
        if (node.left != null && getDirectionsBfs(v, node.left, sb)) {
            sb.append('L');
            return true;
        }
        if (node.right != null && getDirectionsBfs(v, node.right, sb)) {
            sb.append('R');
            return true;
        }
        return false;
    }

    // 1684. Count the Number of Consistent Strings
    public int countConsistentStrings(String allowed, String[] words) {
        boolean[] ch = new boolean[26];
        for (int i = 0; i < allowed.length(); i++) {
            ch[allowed.charAt(i) - 'a'] = true;
        }
        int res = 0;
        for (String str : words) {
            boolean flag = true;
            for (int i = 0; i < str.length(); i++) {
                if (ch[str.charAt(i) - 'a'])
                    continue;
                flag = false;
                break;
            }
            if (flag)
                res++;
        }
        return res;
    }

    // 2506. Count Pairs Of Similar Strings
    public int similarPairs(String[] words) {
        HashSet<Character>[] sets = new HashSet[words.length];
        for (int i = 0; i < words.length; i++) {
            sets[i] = new HashSet<>();
            String word = words[i];
            for (int j = 0; j < word.length(); j++) {
                sets[i].add(word.charAt(j));
            }
        }
        int res = 0;
        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if (sets[i].equals(sets[j])) {
                    res++;
                }
            }
        }
        return res;
    }

    // 650. 2 Keys Keyboard, 问的是最少步数
    public int minSteps(int n) {
        if (n == 1)
            return 0;
        if (n == 2)
            return 2;

        int steps = 0;
        int factor = 2;

        while (n > 1) {
            while (n % factor == 0) {
                steps += factor;
                n /= factor;
            }
            factor++;
        }

        return steps;
    }

    // 1310. XOR Queries of a Subarray
    public int[] xorQueries(int[] arr, int[][] queries) {
        int len = arr.length;
        int[] pre = new int[len];
        pre[0] = arr[0];
        for (int i = 1; i < len; i++) {
            pre[i] = pre[i - 1] ^ arr[i];
        }
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int l = queries[i][0], r = queries[i][1];
            if (l == 0) {
                res[i] = pre[r];
            } else {
                res[i] = pre[r] ^ pre[l - 1];
            }
        }
        return res;
    }

    // 2419. Longest Subarray With Maximum Bitwise AND
    public int longestSubarray(int[] nums) {
        int res = 1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            max = Math.max(max, nums[i]);
        }
        int curNum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == max) {
                curNum++;
            } else {
                res = Math.max(curNum, res);
                curNum = 0;
            }
        }
        res = Math.max(curNum, res);
        return res;
    }

    // 1805. Number of Different Integers in a String
    public int numDifferentIntegers(String word) {
        int p = 0;
        int len = word.length();
        StringBuilder sb = new StringBuilder();
        Set<String> set = new HashSet<>();
        while (p < len) {
            if (word.charAt(p) >= '0' && word.charAt(p) <= '9') {
                sb.append(word.charAt(p));
            } else if (sb.length() != 0) {
                while (sb.length() >= 2 && sb.charAt(0) == '0') {
                    sb.delete(0, 1);
                }
                set.add(sb.toString());
                sb = new StringBuilder();
            }
            p++;
        }
        if (sb.length() != 0) {
            while (sb.length() >= 2 && sb.charAt(0) == '0') {
                sb.delete(0, 1);
            }
            set.add(sb.toString());
        }
        return set.size();
    }

    // 19. Remove Nth Node From End of List
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummyhead = new ListNode(), p1 = dummyhead, p2 = head;
        dummyhead.next = head;
        int i = 0;
        while (i < n) {
            p2 = p2.next;
            i++;
        }
        while (p2 != null) {
            p1 = p1.next;
            p2 = p2.next;
        }
        if (p1.next == null)
            return dummyhead;
        p1.next = p1.next.next;
        return dummyhead.next;
    }

    // 24. Swap Nodes in Pairs
    public ListNode swapPairs(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode dummyhead = new ListNode();
        dummyhead.next = head;
        ListNode p1 = head, p2 = head.next, pre = dummyhead;
        while (p1 != null && p2 != null) {
            ListNode next = p2.next;
            pre.next = p2;
            p2.next = p1;
            p1.next = next;
            pre = p1;
            p1 = next;
            if (p1 == null)
                break;
            p2 = next.next;
        }
        return dummyhead.next;
    }

    // 1721. Swapping Nodes in a Linked List
    public ListNode swapNodes(ListNode head, int k) {
        ListNode dummyhead = new ListNode();
        dummyhead.next = head;
        ListNode p1 = dummyhead, pre1 = dummyhead;
        for (int i = 0; i < k; i++) {
            pre1 = p1;
            p1 = p1.next;
        }
        ListNode p2 = dummyhead, pre2 = p2, cur = p1;
        while (cur != null) {
            cur = cur.next;
            pre2 = p2;
            p2 = p2.next;
        }
        pre2.next = p1;
        pre1.next = p2;
        ListNode temp = p1.next;
        p1.next = p2.next;
        p2.next = temp;
        return dummyhead.next;
    }

    // 1371. Find the Longest Substring Containing Vowels in Even Counts
    public int findTheLongestSubstring(String s) {
        int mask = 0;
        int res = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case 'a':
                    mask ^= 1;
                    break;
                case 'e':
                    mask ^= 2;
                    break;
                case 'i':
                    mask ^= 4;
                    break;
                case 'o':
                    mask ^= 8;
                    break;
                case 'u':
                    mask ^= 16;
                    break;
                default:
                    break;
            }
            if (map.containsKey(mask)) {
                res = Math.max(res, i - map.get(mask));
            } else {
                map.put(mask, i);
            }
        }
        return res;
    }

    // 29. Divide Two Integers
    public int divide(int dividend, int divisor) {
        long sign = 1;
        if (divisor == 1)
            return dividend;
        if ((dividend < 0 && divisor >= 0) || (dividend >= 0 && divisor < 0))
            sign = -1;
        long cur = 0;
        long dividendLong = Math.abs(dividend);
        long divisorLong = Math.abs(divisor);
        for (int i = 30; i >= 0; i--) {
            if (dividendLong >= (divisorLong << i)) {
                cur += (1 << i);
                dividendLong -= (divisorLong << i);
            }
        }
        return (int) (sign * cur);
    }

    // 67. Add Binary
    public String addBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int al = a.length() - 1, bl = b.length() - 1;
        int carry = 0;
        while (al >= 0 || bl >= 0 || carry != 0) {
            if (al >= 0) {
                carry += a.charAt(al) - '0';
                al--;
            }
            if (bl >= 0) {
                carry += b.charAt(bl) - '0';
                bl--;
            }
            if (carry % 2 == 1) {
                sb.append('1');
            } else {
                sb.append('0');
            }
            carry /= 2;
        }
        sb.reverse();
        return sb.toString();
    }

    // 1334. Find the City With the Smallest Number of Neighbors at a Threshold
    // Distance
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], 10001);
        }
        for (int[] edeg : edges) {
            int from = edeg[0];
            int to = edeg[1];
            int distance = edeg[2];
            dist[from][to] = distance;
            dist[to][from] = distance;
        }
        for (int k = 0; k < n; ++k) {
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
        int res = 0;
        int minCity = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int num = 0;
            for (int j = 0; j < n; j++) {
                if (i != j && dist[i][j] <= distanceThreshold) {
                    num++;
                }
            }
            if (minCity >= num) {
                minCity = num;
                res = i;
            }
        }
        return res;
    }

    // 350. Intersection of Two Arrays II
    public int[] intersect(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums1) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        List<Integer> list = new ArrayList<>();
        for (int num : nums2) {
            if (map.containsKey(num)) {
                int temp = map.get(num) - 1;
                if (temp == 0) {
                    map.remove(num);
                } else {
                    map.put(num, temp);
                }
                list.add(num);
            }
        }
        int[] res = new int[list.size()];
        int i = 0;
        for (int num : list) {
            res[i] = num;
            i++;
        }
        return res;
    }

    // 3099. Harshad Number
    public int sumOfTheDigitsOfHarshadNumber(int x) {
        int sum = 0;
        int temp = x;
        while (temp != 0) {
            sum += temp % 10;
            temp /= 10;
        }
        if (x % sum == 0)
            return sum;
        return -1;
    }

    // 3095. Shortest Subarray With OR at Least K I
    public int minimumSubarrayLength(int[] nums, int k) {
        int n = nums.length;
        int min = n + 1;
        for (int i = 0; i < n; i++) {
            int cur = 0;
            for (int j = i; j < n; j++) {
                cur |= nums[j];
                if (cur >= k) {
                    min = Math.min(min, j - i + 1);
                    break;
                }
            }
        }
        if (min > n)
            return -1;
        return min;
    }

    // 539. Minimum Time Difference
    public int findMinDifference(List<String> timePoints) {
        int n = timePoints.size();
        int[] times = new int[n];
        int i = 0;
        for (String time : timePoints) {
            String[] timea = time.split(":");
            int ha = Integer.parseInt(timea[0]);
            int ma = Integer.parseInt(timea[1]);
            times[i++] = ha * 60 + ma;
        }
        Arrays.sort(times);
        int res = Integer.MAX_VALUE;
        for (int j = 0; j < n - 1; j++) {
            int diff = times[j + 1] - times[j];
            res = Math.min(res, diff);
            if (res == 0)
                return 0;
        }
        int diff = 24 * 60 - times[n - 1] + times[0];
        res = Math.min(res, diff);
        return res;
    }

    // 884. Uncommon Words from Two Sentences
    public String[] uncommonFromSentences(String s1, String s2) {
        String[] ss1 = s1.split(" ");
        String[] ss2 = s2.split(" ");
        Set<String> set = new HashSet<>();
        Set<String> mulSet = new HashSet<>();
        for (int i = 0; i < ss1.length; i++) {
            if (set.contains(ss1[i])) {
                mulSet.add(ss1[i]);
            } else {
                set.add(ss1[i]);
            }
        }
        Set<String> set2 = new HashSet<>();
        for (int i = 0; i < ss2.length; i++) {
            if (set2.contains(ss2[i])) {
                mulSet.add(ss2[i]);
            } else {
                set2.add(ss2[i]);
            }
        }
        List<String> res = new ArrayList<>();
        for(String str : set){
            if(mulSet.contains(str)){
                continue;
            }
            if(!set2.contains(str)){
                res.add(str);
            }
        }
        for(String str : set2){
            if(mulSet.contains(str)){
                continue;
            }
            if(!set.contains(str)){
                res.add(str);
            }
        }
        return res.toArray(new String[0]);
    }
}
