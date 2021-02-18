package com.example.architecturebase.network

class UseCase {
    fun createUseCase(posts: List<Post>):  List<Post>{
        return posts.filter {
            !it.title.startsWith("H")
        }.map {
            it.copy(title = it.title + "appendix")
        }.sortedBy {
            it.title
        }.subList(0, posts.size - 3)
    }
}