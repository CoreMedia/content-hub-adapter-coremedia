/** @type { import('@jangaroo/core').IJangarooConfig } */
module.exports = {
  type: "code",
  sencha: {
    name: "com.coremedia.labs.plugins__studio-client.content-hub-adapter-coremedia",
    namespace: "com.coremedia.labs.plugins.adapters.coremedia.client",
    studioPlugins: [
      {
        mainClass: "com.coremedia.labs.plugins.adapters.coremedia.client.ContentHubCoreMediaStudioPlugin",
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
