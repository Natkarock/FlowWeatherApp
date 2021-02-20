package com.natkarock.get_cities.data

data class CitiesResponse(
    val data: List<Data>,
    val links: List<Link>,
    val metadata: Metadata
)