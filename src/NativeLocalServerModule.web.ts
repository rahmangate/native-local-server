import { registerWebModule, NativeModule } from 'expo';

import { NativeLocalServerModuleEvents } from './NativeLocalServer.types';

class NativeLocalServerModule extends NativeModule<NativeLocalServerModuleEvents> {
  PI = Math.PI;
  async setValueAsync(value: string): Promise<void> {
    this.emit('onChange', { value });
  }
  hello() {
    return 'Hello world! 👋';
  }
}

export default registerWebModule(NativeLocalServerModule, 'NativeLocalServerModule');
