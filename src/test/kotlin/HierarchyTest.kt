import kotlin.test.Test
import kotlin.test.assertEquals

class FilterTest {
  @Test
  fun testFilter() {
    val unfiltered: Hierarchy = ArrayBasedHierarchy(
      intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11),
      intArrayOf(0, 1, 2, 3, 1, 0, 1, 0, 1, 1, 2))
    val filteredActual: Hierarchy = unfiltered.filter { nodeId -> nodeId % 3 != 0 }
    val filteredExpected: Hierarchy = ArrayBasedHierarchy(
      intArrayOf(1, 2, 5, 8, 10, 11),
      intArrayOf(0, 1, 1, 0, 1, 2))
    assertEquals(filteredExpected.formatString(), filteredActual.formatString())
  }

  @Test
  fun givenEmptyHierarchy_whenFilter_thenReturnsEmptyHierarchy() {
    val unfiltered: Hierarchy = ArrayBasedHierarchy(
      intArrayOf(),
      intArrayOf()
    )
    val filteredActual = unfiltered.filter { true }
    val filteredExpected: Hierarchy = ArrayBasedHierarchy(
      intArrayOf(),
      intArrayOf()
    )
    assertEquals(filteredExpected.formatString(), filteredActual.formatString())
  }

  @Test
  fun givenAllNodesMatch_whenFilter_thenReturnsOriginalHierarchy() {
    val unfiltered: Hierarchy = ArrayBasedHierarchy(
      intArrayOf(1, 2, 3, 4),
      intArrayOf(0, 1, 2, 1))

    val filteredActual = unfiltered.filter { true }

    assertEquals(unfiltered.formatString(), filteredActual.formatString())
  }

  @Test
  fun givenNoNodesMatch_whenFilter_thenReturnsEmptyHierarchy() {
    val unfiltered: Hierarchy = ArrayBasedHierarchy(
      intArrayOf(1, 2, 3, 4),
      intArrayOf(0, 1, 2, 1))

    val filteredActual = unfiltered.filter { false }
    val filteredExpected: Hierarchy = ArrayBasedHierarchy(
      intArrayOf(),
      intArrayOf())

    assertEquals(filteredExpected.formatString(), filteredActual.formatString())
  }

  @Test
  fun givenRootFails_whenFilter_thenEntireSubtreeIsRemoved() {
    val unfiltered: Hierarchy = ArrayBasedHierarchy(
      intArrayOf(1, 2, 3, 4),
      intArrayOf(0, 1, 2, 0))

    val filteredActual = unfiltered.filter { nodeId -> nodeId != 1 }
    val filteredExpected: Hierarchy = ArrayBasedHierarchy(
      intArrayOf(4),
      intArrayOf(0))

    assertEquals(filteredExpected.formatString(), filteredActual.formatString())
  }

  @Test
  fun givenParentFails_whenFilter_thenChildIsAlsoRemoved() {
    val unfiltered: Hierarchy = ArrayBasedHierarchy(
      intArrayOf(1, 2, 3, 4),
      intArrayOf(0, 1, 2, 0))

    val filteredActual = unfiltered.filter { nodeId -> nodeId != 2 }
    val filteredExpected: Hierarchy = ArrayBasedHierarchy(
      intArrayOf(1, 4),
      intArrayOf(0, 0))

    assertEquals(filteredExpected.formatString(), filteredActual.formatString())
  }

  @Test
  fun givenMultipleRoots_whenFilter_thenEachTreeIsFilteredIndependently() {
    val unfiltered: Hierarchy = ArrayBasedHierarchy(
      intArrayOf(1, 2, 3, 4, 5, 6),
      intArrayOf(0, 1, 0, 1, 2, 0))

    val filteredActual = unfiltered.filter { nodeId -> nodeId != 3 }
    val filteredExpected: Hierarchy = ArrayBasedHierarchy(
      intArrayOf(1, 2, 6),
      intArrayOf(0, 1, 0))

    assertEquals(filteredExpected.formatString(), filteredActual.formatString())
  }

  @Test
  fun givenSingleNodeMatching_whenFilter_thenReturnsSingleNode() {
    val unfiltered: Hierarchy = ArrayBasedHierarchy(
      intArrayOf(42),
      intArrayOf(0))

    val filteredActual = unfiltered.filter { nodeId -> nodeId == 42 }
    val filteredExpected: Hierarchy = ArrayBasedHierarchy(
      intArrayOf(42),
      intArrayOf(0))

    assertEquals(filteredExpected.formatString(), filteredActual.formatString())
  }

  @Test
  fun givenSingleNodeNotMatching_whenFilter_thenReturnsEmptyHierarchy() {
    val unfiltered: Hierarchy = ArrayBasedHierarchy(
      intArrayOf(42),
      intArrayOf(0))

    val filteredActual = unfiltered.filter { nodeId -> nodeId != 42 }
    val filteredExpected: Hierarchy = ArrayBasedHierarchy(
      intArrayOf(),
      intArrayOf())

    assertEquals(filteredExpected.formatString(), filteredActual.formatString())
  }
}