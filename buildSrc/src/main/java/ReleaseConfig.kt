object ReleaseConfig {
    private const val major = 0
    private const val minor = 0
    private const val patch = 1

    val appVersionName = getVersionName()
    const val appVersionCode = major * 10000 + minor * 1000 + patch * 100

    private fun getVersionName() = "$major.$minor.$patch"
}
