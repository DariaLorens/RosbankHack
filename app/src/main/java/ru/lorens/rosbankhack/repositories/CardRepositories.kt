package ru.lorens.rosbankhack.repositories

import ru.lorens.rosbankhack.rest.Card

object CardRepositories {
    var cards = mutableListOf<Card>()

    var deletedCards = mutableListOf<Card>()

    fun getCardsForArticleId(id: Int): Card {
        return deletedCards.first { it.article.id == id }
    }

    fun deleteCard(element: Card) {
        deletedCards.add(element)
        cards.remove(element)
    }
}