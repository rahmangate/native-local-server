import { NativeModule, requireNativeModule } from "expo";

import { NativeLocalServerModuleEvents } from "./NativeLocalServer.types";

declare class NativeLocalServerModule extends NativeModule<NativeLocalServerModuleEvents> {
  PI: number;
  hello(): string;
  setValueAsync(value: string): Promise<void>;
  startServer(documentPath: string): Promise<string>;
  stopServer(): Promise<void>;
}

// This call loads the native module object from the JSI.
export default requireNativeModule<NativeLocalServerModule>(
  "NativeLocalServer"
);
