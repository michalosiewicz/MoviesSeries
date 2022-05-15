package com.micosi.moviesseries.mappers

interface Mapper<TInput, TOutput> {

    fun map(input: TInput): TOutput
}