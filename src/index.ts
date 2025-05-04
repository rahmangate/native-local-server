// Reexport the native module. On web, it will be resolved to NativeLocalServerModule.web.ts
// and on native platforms to NativeLocalServerModule.ts
export { default } from './NativeLocalServerModule';
export { default as NativeLocalServerView } from './NativeLocalServerView';
export * from  './NativeLocalServer.types';
