import * as React from 'react';

import { NativeLocalServerViewProps } from './NativeLocalServer.types';

export default function NativeLocalServerView(props: NativeLocalServerViewProps) {
  return (
    <div>
      <iframe
        style={{ flex: 1 }}
        src={props.url}
        onLoad={() => props.onLoad({ nativeEvent: { url: props.url } })}
      />
    </div>
  );
}
