data class Coords2D(val x: Int, val y: Int) {
    fun valid(maxX : Int, maxY : Int, minX :Int = 0, minY : Int = 0) : Boolean {
        return x >= minX && x <= maxX && y>= minY && y <= maxY
    }
    fun inMatrix(max : Int) : Boolean {
        return valid(max, max)
    }
}
operator fun Coords2D.plus(other: Coords2D) = Coords2D(x + other.x, y + other.y)
operator fun Coords2D.minus(other: Coords2D) = Coords2D(x - other.x, y - other.y)
operator fun Coords2D.times(other: Coords2D) = Coords2D(x * other.x, y * other.y)

enum class Directions {
    TOP_LEFT {
        override fun next(actual: Coords2D): Coords2D {
            return Coords2D(actual.x-1, actual.y-1)
        }
    },
    TOP {
        override fun next(actual: Coords2D): Coords2D {
            return Coords2D(actual.x, actual.y-1)
        }
        override fun move(actual: Coords2D): Coords2D {
            return Coords2D(actual.x, actual.y-1)
        }
        override fun turnRight(): Directions {
            return RIGHT
        }
    },
    TOP_RIGHT {
        override fun next(actual: Coords2D): Coords2D {
            return Coords2D(actual.x+1, actual.y-1)
        }
    },
    RIGHT {
        override fun next(actual: Coords2D): Coords2D {
            return Coords2D(actual.x+1, actual.y)
        }
        override fun move(actual: Coords2D): Coords2D {
            return Coords2D(actual.x+1, actual.y)
        }
        override fun turnRight(): Directions {
            return DOWN
        }
    },
    DOWN_RIGHT {
        override fun next(actual: Coords2D): Coords2D {
            return Coords2D(actual.x+1, actual.y+1)
        }
    },
    DOWN {
        override fun next(actual: Coords2D): Coords2D {
            return Coords2D(actual.x, actual.y+1)
        }
        override fun move(actual: Coords2D): Coords2D {
            return Coords2D(actual.x, actual.y+1)
        }
        override fun turnRight(): Directions {
            return LEFT
        }
    },
    DOWN_LEFT {
        override fun next(actual: Coords2D): Coords2D {
            return Coords2D(actual.x-1, actual.y+1)
        }
    },
    LEFT {
        override fun next(actual: Coords2D): Coords2D {
            return Coords2D(actual.x-1, actual.y)
        }
        override fun move(actual: Coords2D): Coords2D {
            return Coords2D(actual.x-1, actual.y)
        }
        override fun turnRight(): Directions {
            return TOP
        }
    };
    abstract fun next(actual : Coords2D) : Coords2D

    open fun move(actual : Coords2D) : Coords2D {
        return actual
    }

    open fun turnRight() : Directions {
        return this
    }

}

val SQUARED_DIRECTIONS = listOf(Directions.TOP, Directions.RIGHT, Directions.DOWN, Directions.LEFT)
val HORIZONTAL_DIRECTIONS = listOf(Directions.LEFT, Directions.RIGHT)
val VERTICAL_DIRECTIONS = listOf(Directions.TOP, Directions.DOWN)
