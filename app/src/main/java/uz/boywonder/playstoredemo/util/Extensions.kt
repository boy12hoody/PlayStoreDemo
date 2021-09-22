package uz.boywonder.playstoredemo.util

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavGraph

/**
 *  Safely navigate via the given NavDirections
 *
 *  A fix for [Navigation action cannot be found in the current destination] crash
 * @param directions directions that describe this navigation operation
 *
 * @author @BoyWonder
 */
fun NavController.navigateSafe(directions: NavDirections) {
    currentDestination?.getAction(directions.actionId)?.run { navigate(directions) }
}


/**
 *  Safely navigate via the given Action
 *
 *  A fix for [Navigation action cannot be found in the current destination] crash
 * @param resActionId action that describes this navigation operation
 *
 * @author @BoyWonder
 */
fun NavController.navigateSafe(@IdRes resActionId: Int) {
    val destinationId = currentDestination?.getAction(resActionId)?.destinationId
    currentDestination?.let { node ->
        val currentNode = when (node) {
            is NavGraph -> node
            else -> node.parent
        }
        if (destinationId != null) {
            currentNode?.findNode(destinationId)?.let { navigate(resActionId) }
        }
    }
}


/**
 *  Safely navigate to a destination from the current navigation graph
 *
 *  A fix for [Navigation action cannot be found in the current destination] crash
 * @param currentDestinationId a current destination id
 * @param navigateToDestinationId a destination id to navigate to
 *
 * @author @BoyWonder
 */
fun NavController.navigateSafe(
    @IdRes currentDestinationId: Int,
    @IdRes navigateToDestinationId: Int,
    args: Bundle? = null
) {
    if (currentDestinationId == currentDestination?.id) {
        navigate(navigateToDestinationId, args)
    }
}

