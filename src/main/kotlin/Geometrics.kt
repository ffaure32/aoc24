data class Coords2D(val x: Int, val y: Int) {
    fun valid(maxX : Int, maxY : Int, minX :Int = 0, minY : Int = 0) : Boolean {
        return x >= minX && x <= maxX && y>= minY && y <= maxY
    }
    fun inMatrix(max : Int) : Boolean {
        return valid(max, max)
    }
}

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
    };
    abstract fun next(actual : Coords2D) : Coords2D

}