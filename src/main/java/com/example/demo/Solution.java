package com.example.demo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
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
        Queue<int[]> queue = new temp<>();
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
}
