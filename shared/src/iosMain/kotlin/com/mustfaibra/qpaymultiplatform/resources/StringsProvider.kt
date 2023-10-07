package com.mustfaibra.qpaymultiplatform.resources

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.format

actual class StringsProvider {
	actual fun get(
		stringId: StringResource,
		args: List<Any>
	): String {
		return if (args.isEmpty()) {
			StringDesc.Resource(stringId).localized()
		}
		else {
			stringId.format(*args.toTypedArray()).localized()
		}
	}
}
