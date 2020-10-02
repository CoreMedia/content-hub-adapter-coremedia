# Editorial Quick Start

--------------------------------------------------------------------------------

\[[Up](README.md)\] \[[Top](#top)\]

--------------------------------------------------------------------------------

## Introducing

The coremedia-contenthub-adapter is providing a browsable tree of the current (preferred)
site. The image below is showing the result after successfully configuring the adapter.

![Image1: Studio appearance with configured adapters](images/editorial/editorial-documentation_1.png)

# Basic configuration
The navigation adapter is coming with some minor configuration options (basically the common )

## Browsing the content-hub-adapter-coremedia
Depending on the configuration of the adapter, the appearance of the tree may vary. The following sections 
are taking care of all configuration **places** and **options**.

## Basic adapter configuration
This section is covering the two possibilities to enable the content-hub-adapter-coremedia integration. Please note that those
options are valid for all content-hub-adapters. Before configuring the adapter, please refer to the documentation [Content hub configuration](https://documentation.coremedia.com/cmcc-10/artifacts/2004/webhelp/deployment-en/content/Studio-Contenthub-Configuration.html)
for preliminary steps.

### Global adapter configuration
To enable the content-hub-adapter-coremedia for all sites, it is necessary to create a CMSettings document inside the location:
* contenthub.studio.global-configuration-path
For convenience reasons, naming proposal of this document is "coremedia" (postfix of the extension name)

### Site specific adapter configuration
To enable the content-hub-adapter-coremedia for a single site, it is necessary to create a CMSettings document inside the location:
* contenthub.studio.site-configuration-path
For convenience reasons, naming proposal of this document is "coremedia" (postfix of the extension name)


## Detailed adapter configuration

### Basic structure
The table below is showing the initial toplevel entry for all content-hub-connector configurations.

| Key         | Type       | Required   |
|-------------|------------|------------|
| connections | StructList | Yes        |

After creation of the initial struct list called **connections** the next step is to create the first entry. This can be done 
in Studio with the struct editor by pressing "add Item to ListProperty". The table below is showing the entries which are common for all connectors.

| Key           | Type       | Value                 | Required   |
|---------------|------------|------------           |------------|
| connectionId  | String      | <YOUR_CHOOSEN_ID>    | Yes        |
| factoryId     | String      | coremedia-navigation | Yes        |
| enabled       | Boolean     | true or false        | Yes        |
| settings       | Struct     |                      | Yes        |
