interface Hierarchy {
  /** The number of nodes in the hierarchy. */
  val size: Int

  /**
   * Returns the unique ID of the node identified by the hierarchy index. The depth for this node will be `depth(index)`.
   * @param index must be non-negative and less than [size]
   * */
  fun nodeId(index: Int): Int

  /**
   * Returns the depth of the node identified by the hierarchy index. The unique ID for this node will be `nodeId(index)`.
   * @param index must be non-negative and less than [size]
   * */
  fun depth(index: Int): Int

  fun formatString(): String {
    return (0 until size).joinToString(
      separator = ", ",
      prefix = "[",
      postfix = "]"
    ) { i -> "${nodeId(i)}:${depth(i)}" }
  }
}

/**
 * A node is present in the filtered hierarchy if its node ID passes the predicate and all of its ancestors pass it as well.
 */
fun Hierarchy.filter(nodeIdPredicate: (Int) -> Boolean): Hierarchy {
    val resultNodeIds = mutableListOf<Int>()
    val resultDepths = mutableListOf<Int>()

    val acceptedAtDepth = mutableMapOf<Int, Boolean>()

    for (i in 0 until size) {
        val id = nodeId(i)
        val depth = depth(i)

        val parentAccepted = if (depth == 0) true else acceptedAtDepth[depth - 1] == true
        val accepted = parentAccepted && nodeIdPredicate(id)

        acceptedAtDepth[depth] = accepted

        if (accepted) {
            resultNodeIds.add(id)
            resultDepths.add(depth)
        }
    }

    return ArrayBasedHierarchy(
        resultNodeIds.toIntArray(),
        resultDepths.toIntArray()
    )
}

class ArrayBasedHierarchy(
  private val myNodeIds: IntArray,
  private val myDepths: IntArray,
) : Hierarchy {
  override val size: Int = myDepths.size

  override fun nodeId(index: Int): Int = myNodeIds[index]

  override fun depth(index: Int): Int = myDepths[index]
}