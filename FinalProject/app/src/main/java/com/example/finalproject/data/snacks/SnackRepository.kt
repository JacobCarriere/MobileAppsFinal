package com.example.finalproject.data.snacks

import kotlinx.coroutines.flow.Flow

class SnackRepository(private val snackDao: SnackDao) {

    val allSnacks: Flow<List<SnackEntity>> = snackDao.getAllSnacks()

    suspend fun insert(snack: SnackEntity) =
        snackDao.insertSnack(snack)

    suspend fun insertAll(snacks: List<SnackEntity>) =
        snackDao.insertAll(snacks)

    suspend fun update(snack: SnackEntity) =
        snackDao.updateSnack(snack)

    suspend fun count(): Int =
        snackDao.count()
}

