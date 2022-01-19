/** @type { import('@jangaroo/core').IJangarooConfig } */
module.exports = {
  type: "code",
  sencha: {
    name: "com.coremedia.labs.plugins__studio-client.content-hub-adapter-coremedia",
    namespace: "com.coremedia.labs.plugins.adapters.coremedia",
    studioPlugins: [
      {
        mainClass: "com.coremedia.labs.plugins.adapters.coremedia.ContentHubCoreMediaStudioPlugin",
        name: "Content Hub - CoreMedia",
      },
    ],
  },
  command: {
    build: {
      ignoreTypeErrors: true
    },
  },
};
