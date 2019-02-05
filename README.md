# ICE - Integrated Clojure Environment

A neovim plugin for developing Clojure(script), written in Clojurescript.

## Getting started

Ensure you have the neovim npm package installed:
`npm install -g neovim` && `:healthcheck` to verify you have the nodejs host
available.
`npm install -g source-map-support` for development, enables source mapping.

Run the `./build` script in this repository.

Verify the plugin is loaded my using the `:SetMyLine` function.

## Run Tests

Start a repl with: `clj -Arepl`

Run test suite with: `clj -Atest`
