package com.application.nuntium.model

data class Source(
	val sources: List<SourcesItem>,
	val status: String? = null
)

data class SourcesItem(
	val country: String? = null,
	val name: String? = null,
	val description: String? = null,
	val language: String? = null,
	val id: String? = null,
	val category: String? = null,
	val url: String? = null
)

