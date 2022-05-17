package com.micosi.moviesseries.mappers

import com.micosi.moviesseries.api.models.ApiMovie
import com.micosi.moviesseries.db.DBReference.authFB
import com.micosi.moviesseries.models.Movie

class ApiMovieToListMovieMapper : Mapper<ApiMovie?, List<Movie>> {

    override fun map(input: ApiMovie?): List<Movie> {
        val listMovie = mutableListOf<Movie>()
        if (input != null) {
            for (result in input.results) {
                listMovie.add(
                    Movie(
                        authFB.currentUser!!.uid,
                        result.title,
                        result.image,
                        result.year
                    )
                )
            }
        }
        return listMovie
    }
}