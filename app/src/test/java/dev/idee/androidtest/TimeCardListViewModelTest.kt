package dev.idee.androidtest

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class TimeCardListViewModelTest {

    lateinit var viewModel: TimeCardListViewModel
    lateinit var cardModel: TimeCardModel
    @Before
    fun setup() {

        MockitoAnnotations.initMocks(this)
        viewModel = Mockito.mock(TimeCardListViewModel::class.java)
        cardModel = Mockito.mock(TimeCardModel::class.java)

    }

    @Test
    fun listsStartAtZero() {
        Assert.assertEquals(viewModel.timeCardList.size, 0)
    }

}