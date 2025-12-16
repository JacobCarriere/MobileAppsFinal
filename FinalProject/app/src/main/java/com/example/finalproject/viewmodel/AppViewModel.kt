package com.example.finalproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.snacks.SnackEntity
import com.example.finalproject.data.snacks.SnackRepository
import com.example.finalproject.model.Movie
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AppViewModel(
    private val snackRepository: SnackRepository
) : ViewModel() {

    // ---------- Movies ----------
    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie.asStateFlow()

    private val _selectedDate = MutableStateFlow<String?>(null)
    val selectedDate: StateFlow<String?> = _selectedDate.asStateFlow()

    fun selectMovie(movie: Movie, date: String) {
        _selectedMovie.value = movie
        _selectedDate.value = date
    }

    // ---------- Snacks ----------
    val snacks: StateFlow<List<SnackEntity>> = snackRepository.allSnacks
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun increaseSnack(snack: SnackEntity) = viewModelScope.launch {
        snackRepository.update(
            snack.copy(amountInCart = snack.amountInCart + 1)
        )
    }

    fun decreaseSnack(snack: SnackEntity) = viewModelScope.launch {
        snackRepository.update(
            snack.copy(amountInCart = (snack.amountInCart - 1).coerceAtLeast(0))
        )
    }

    fun snacksInCart(): List<SnackEntity> =
        snacks.value.filter { it.amountInCart > 0 }

    fun snacksTotal(): Double =
        snacksInCart().sumOf { it.price * it.amountInCart }

    fun clearCart() = viewModelScope.launch {
        snacks.value
            .filter { it.amountInCart > 0 }
            .forEach { snack ->
                snackRepository.update(
                    snack.copy(amountInCart = 0)
                )
            }
    }


    /**
     * Ensures default snacks exist exactly once.
     * Safe to call multiple times.
     */
    fun ensureSnacksExist() = viewModelScope.launch {
        if (snackRepository.count() == 0) {
            snackRepository.insertAll(
                listOf(
                    SnackEntity(
                        name = "Popcorn",
                        description = "Butter popcorn",
                        price = 5.0
                    ),
                    SnackEntity(
                        name = "Soda",
                        description = "Refreshing soda",
                        price = 3.0
                    ),
                    SnackEntity(
                        name = "Candy",
                        description = "Sweet candy",
                        price = 2.5
                    )
                )
            )
        }
    }
}
