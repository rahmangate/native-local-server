import { requireNativeView } from 'expo';
import * as React from 'react';

import { NativeLocalServerViewProps } from './NativeLocalServer.types';

const NativeView: React.ComponentType<NativeLocalServerViewProps> =
  requireNativeView('NativeLocalServer');

export default function NativeLocalServerView(props: NativeLocalServerViewProps) {
  return <NativeView {...props} />;
}
