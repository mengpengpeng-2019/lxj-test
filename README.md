# lxj-test
测试项目

## Java 算法测试用例

本仓库包含二分查找算法示例及对应的 Java 测试用例，包括：

- 在有序数组中查找目标值下标
- 在旋转有序数组中用二分法查找最大值

运行方式：

```bash
mkdir -p out
javac -d out src/main/java/com/lxj/algorithm/BinarySearch.java src/test/java/com/lxj/algorithm/BinarySearchTest.java
java -cp out com.lxj.algorithm.BinarySearchTest
```
